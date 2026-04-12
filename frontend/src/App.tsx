import './App.css'
import { AuthFeatureShell } from './features/auth/AuthFeatureShell'
import { ChatFeatureShell } from './features/chat/ChatFeatureShell'
import { projectMetadata } from './shared/lib/projectMetadata'
import { PageSection } from './shared/ui/PageSection'

function App() {
  return (
    <main className="app-shell">
      <header className="page-section">
        <h1>fullstack-demo</h1>
        <p>Frontend shell aligned with the feature-based architecture baseline.</p>
      </header>

      <PageSection title="Current bootstrap status">
        <ul>
          <li>Package base: {projectMetadata.backendPackageBase}</li>
          <li>CI policy: {projectMetadata.ciPolicy}</li>
          <li>UI structure: features/auth, features/chat, shared/lib, shared/ui</li>
        </ul>
      </PageSection>

      <AuthFeatureShell />
      <ChatFeatureShell />
    </main>
  )
}

export default App
