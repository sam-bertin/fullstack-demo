import { zodResolver } from '@hookform/resolvers/zod'
import axios from 'axios'
import { useState } from 'react'
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

export default function AuthPage() {
  const [authMode, setAuthMode] = useState<AuthMode>('register')
  const [feedback, setFeedback] = useState<string>(initialFeedback)
  const [isSubmitting, setIsSubmitting] = useState<boolean>(false)

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

  const switchMode = (mode: AuthMode): void => {
    setAuthMode(mode)
    setFeedback(initialFeedback)
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
            className={authMode === 'register' ? 'active' : ''}
            onClick={() => switchMode('register')}
          >
            Register
          </button>
          <button
            type="button"
            className={authMode === 'login' ? 'active' : ''}
            onClick={() => switchMode('login')}
          >
            Login
          </button>
        </div>

        {authMode === 'register' ? (
          <form onSubmit={registerForm.handleSubmit(onRegisterSubmit)} className="auth-form">
            <label>
              Email
              <input type="email" {...registerForm.register('email')} />
              <span className="error">{registerForm.formState.errors.email?.message}</span>
            </label>

            <label>
              Username
              <input type="text" {...registerForm.register('username')} />
              <span className="error">{registerForm.formState.errors.username?.message}</span>
            </label>

            <label>
              Password
              <input type="password" {...registerForm.register('password')} />
              <span className="error">{registerForm.formState.errors.password?.message}</span>
            </label>

            <button type="submit" disabled={isSubmitting}>
              {isSubmitting ? 'Envoi...' : 'Créer un compte'}
            </button>
          </form>
        ) : (
          <form onSubmit={loginForm.handleSubmit(onLoginSubmit)} className="auth-form">
            <label>
              Email
              <input type="email" {...loginForm.register('email')} />
              <span className="error">{loginForm.formState.errors.email?.message}</span>
            </label>

            <label>
              Password
              <input type="password" {...loginForm.register('password')} />
              <span className="error">{loginForm.formState.errors.password?.message}</span>
            </label>

            <button type="submit" disabled={isSubmitting}>
              {isSubmitting ? 'Envoi...' : 'Se connecter'}
            </button>
          </form>
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
