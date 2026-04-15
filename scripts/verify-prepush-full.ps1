param(
    [switch]$SkipBackendChecks
)

$ErrorActionPreference = 'Stop'

$repoRoot = (Resolve-Path (Join-Path $PSScriptRoot "..")).Path
$backendDir = Join-Path $repoRoot "backend/backend"
$frontendDir = Join-Path $repoRoot "frontend"
$apiTestsDir = Join-Path $repoRoot "api-tests"

function Invoke-Step {
    param(
        [string]$Name,
        [scriptblock]$Action
    )

    Write-Host "`n==> $Name" -ForegroundColor Cyan
    & $Action
}

function Test-BackendHealth {
    try {
        $response = Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -UseBasicParsing -TimeoutSec 3
        return $response.StatusCode -eq 200
    }
    catch {
        return $false
    }
}

function Invoke-BackendCommand {
    param([string[]]$Arguments)

    Push-Location $backendDir
    try {
        & .\mvnw.cmd @Arguments
        if ($LASTEXITCODE -ne 0) {
            throw "Backend command failed: .\\mvnw.cmd $($Arguments -join ' ')"
        }
    }
    finally {
        Pop-Location
    }
}

try {
    Invoke-Step "Start PostgreSQL (docker compose)" {
        Push-Location $repoRoot
        try {
            docker compose up -d postgres
            if ($LASTEXITCODE -ne 0) {
                throw "Unable to start postgres with docker compose"
            }
        }
        finally {
            Pop-Location
        }
    }

    Invoke-Step "Verify backend health" {
        if (-not (Test-BackendHealth)) {
            throw "Backend is not healthy on http://localhost:8080. Start backend before running this script."
        }
        Write-Host "Backend is healthy on http://localhost:8080"
    }

    if (-not $SkipBackendChecks) {
        Invoke-Step "Backend checks (checkstyle + tests + package)" {
            Invoke-BackendCommand -Arguments @("checkstyle:check")
            Invoke-BackendCommand -Arguments @("test")
            Invoke-BackendCommand -Arguments @("-DskipTests", "clean", "package")
        }
    }

    Invoke-Step "API smoke (Bruno auth)" {
        Push-Location $apiTestsDir
        try {
            $suffix = Get-Date -Format "yyyyMMddHHmmss"
            $brunoArgs = @(
                "run",
                "./auth",
                "--env",
                "ci",
                "--env-var",
                "authEmail=bruno.auth.$suffix@example.com",
                "--env-var",
                "authUsername=brunouser$suffix",
                "--env-var",
                "authPass=testpass1",
                "--env-var",
                "invalidPass=testpass2"
            )

            & npx "@usebruno/cli" @brunoArgs

            if ($LASTEXITCODE -ne 0) {
                throw "Bruno API smoke tests failed"
            }
        }
        finally {
            Pop-Location
        }
    }

    Invoke-Step "Frontend verify:prepush" {
        Push-Location $frontendDir
        try {
            npm run verify:prepush
            if ($LASTEXITCODE -ne 0) {
                throw "Frontend pre-push verification failed"
            }
        }
        finally {
            Pop-Location
        }
    }

    Write-Host "`nPre-push full verification completed successfully." -ForegroundColor Green
}
catch {
    Write-Error $_
    exit 1
}
