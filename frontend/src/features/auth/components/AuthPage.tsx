import { zodResolver } from '@hookform/resolvers/zod'
import axios from 'axios'
import { type KeyboardEvent as ReactKeyboardEvent, useRef, useState } from 'react'
import { useForm } from 'react-hook-form'
import { loginUser, registerUser } from '../hooks/useAuthApi'
import {
  type LoginFormValues,
  loginSchema,
  type RegisterFormValues,
  registerSchema,
} from '../schemas/authSchemas'

const initialFeedback = 'Renseigne tes informations pour tester le socle auth.'

type AuthMode = 'register' | 'login'
const AUTH_TAB_ORDER: AuthMode[] = ['register', 'login']

export default function AuthPage() {
  const [authMode, setAuthMode] = useState<AuthMode>('register')
  const [feedback, setFeedback] = useState<string>(initialFeedback)
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false)
  const registerTabRef = useRef<HTMLButtonElement>(null)
  const loginTabRef = useRef<HTMLButtonElement>(null)

  const registerForm = useForm<RegisterFormValues>({
    resolver: zodResolver(registerSchema),
    defaultValues: {
      email: '',
      username: '',
      password: '',
    },
  })

  const loginForm = useForm<LoginFormValues>({
    resolver: zodResolver(loginSchema),
    defaultValues: {
      email: '',
      password: '',
    },
  })

  const focusTab = (mode: AuthMode): void => {
    if (mode === 'register') {
      registerTabRef.current?.focus()
      return
    }

    loginTabRef.current?.focus()
  }

  const switchMode = (mode: AuthMode, focusSelectedTab = false): void => {
    setAuthMode(mode)
    setFeedback(initialFeedback)

    if (focusSelectedTab) {
      requestAnimationFrame(() => {
        focusTab(mode)
      })
    }
  }

  const onTabKeyDown = (event: ReactKeyboardEvent<HTMLButtonElement>, currentMode: AuthMode): void => {
    const currentIndex = AUTH_TAB_ORDER.indexOf(currentMode)
    const lastIndex = AUTH_TAB_ORDER.length - 1

    let nextMode: AuthMode | null = null

    switch (event.key) {
      case 'ArrowRight':
        nextMode = AUTH_TAB_ORDER[(currentIndex + 1) % AUTH_TAB_ORDER.length]
        break
      case 'ArrowLeft':
        nextMode = AUTH_TAB_ORDER[(currentIndex - 1 + AUTH_TAB_ORDER.length) % AUTH_TAB_ORDER.length]
        break
      case 'Home':
        nextMode = AUTH_TAB_ORDER[0]
        break
      case 'End':
        nextMode = AUTH_TAB_ORDER[lastIndex]
        break
      default:
        return
    }

    event.preventDefault()
    switchMode(nextMode, true)
  }

  const onRegisterSubmit = async (values: RegisterFormValues): Promise<void> => {
    setIsSubmitting(true)
    try {
      const response = await registerUser(values)
      setFeedback(`Register OK: ${response.data?.email ?? 'N/A'}`)
      registerForm.reset()
    } catch (error) {
      setFeedback(readApiError(error))
    } finally {
      setIsSubmitting(false)
    }
  }

  const onLoginSubmit = async (values: LoginFormValues): Promise<void> => {
    setIsSubmitting(true)
    try {
      const response = await loginUser(values)
      setFeedback(
        `Login OK: ${response.data?.user.email ?? 'N/A'} (${response.data?.authMode ?? 'UNKNOWN'})`,
      )
      loginForm.reset()
    } catch (error) {
      setFeedback(readApiError(error))
    } finally {
      setIsSubmitting(false)
    }
  }

  return (
    <main className="auth-shell">
      <section className="auth-card">
        <header>
          <p className="eyebrow">LiveChat MVP Socle</p>
          <h1>Validation Auth Sans JWT</h1>
          <p className="subtitle">
            Ce sprint valide register/login avec BCrypt et architecture backend en couches.
          </p>
        </header>

        <div className="mode-switch" role="tablist" aria-label="Choisir un mode auth">
          <button
            type="button"
            ref={registerTabRef}
            id="auth-tab-register"
            role="tab"
            aria-selected={authMode === 'register'}
            aria-controls="auth-panel-register"
            tabIndex={authMode === 'register' ? 0 : -1}
            className={authMode === 'register' ? 'active' : ''}
            onClick={() => switchMode('register')}
            onKeyDown={(event) => onTabKeyDown(event, 'register')}
          >
            Register
          </button>
          <button
            type="button"
            ref={loginTabRef}
            id="auth-tab-login"
            role="tab"
            aria-selected={authMode === 'login'}
            aria-controls="auth-panel-login"
            tabIndex={authMode === 'login' ? 0 : -1}
            className={authMode === 'login' ? 'active' : ''}
            onClick={() => switchMode('login')}
            onKeyDown={(event) => onTabKeyDown(event, 'login')}
          >
            Login
          </button>
        </div>

        {authMode === 'register' ? (
          <div role="tabpanel" id="auth-panel-register" aria-labelledby="auth-tab-register">
            <form onSubmit={registerForm.handleSubmit(onRegisterSubmit)} className="auth-form">
              <label htmlFor="register-email">
                <span>Email</span>
                <input id="register-email" type="email" {...registerForm.register('email')} />
                <span className="error">{registerForm.formState.errors.email?.message}</span>
              </label>

              <label htmlFor="register-username">
                <span>Username</span>
                <input id="register-username" type="text" {...registerForm.register('username')} />
                <span className="error">{registerForm.formState.errors.username?.message}</span>
              </label>

              <label htmlFor="register-password">
                <span>Password</span>
                <input
                  id="register-password"
                  type="password"
                  {...registerForm.register('password')}
                />
                <span className="error">{registerForm.formState.errors.password?.message}</span>
              </label>

              <button type="submit" disabled={isSubmitting}>
                {isSubmitting ? 'Envoi...' : 'Créer un compte'}
              </button>
            </form>
          </div>
        ) : (
          <div role="tabpanel" id="auth-panel-login" aria-labelledby="auth-tab-login">
            <form onSubmit={loginForm.handleSubmit(onLoginSubmit)} className="auth-form">
              <label htmlFor="login-email">
                <span>Email</span>
                <input id="login-email" type="email" {...loginForm.register('email')} />
                <span className="error">{loginForm.formState.errors.email?.message}</span>
              </label>

              <label htmlFor="login-password">
                <span>Password</span>
                <input id="login-password" type="password" {...loginForm.register('password')} />
                <span className="error">{loginForm.formState.errors.password?.message}</span>
              </label>

              <button type="submit" disabled={isSubmitting}>
                {isSubmitting ? 'Envoi...' : 'Se connecter'}
              </button>
            </form>
          </div>
        )}

        <p className="feedback" aria-live="polite">
          {feedback}
        </p>
      </section>
    </main>
  )
}

function readApiError(error: unknown): string {
  if (axios.isAxiosError(error)) {
    const message = error.response?.data?.message
    const code = error.response?.data?.errorCode

    if (typeof message === 'string' && message.length > 0) {
      return code ? `${message} (${code})` : message
    }
  }

  return 'Une erreur inattendue est survenue.'
}
