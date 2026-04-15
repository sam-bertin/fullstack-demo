import { cleanup, fireEvent, render, screen, waitFor } from '@testing-library/react'
import { afterEach, describe, expect, it, vi } from 'vitest'
import AuthPage from './AuthPage'

vi.mock('../hooks/useAuthApi', () => ({
  loginUser: vi.fn(),
  registerUser: vi.fn(),
}))

afterEach(() => {
  cleanup()
})

describe('AuthPage tabs accessibility', () => {
  it('provides a complete tabs pattern with selected tab and panel association', () => {
    render(<AuthPage />)

    const tabList = screen.getByRole('tablist', { name: 'Choisir un mode auth' })
    const registerTab = screen.getByRole('tab', { name: 'Register' })
    const loginTab = screen.getByRole('tab', { name: 'Login' })
    const panel = screen.getByRole('tabpanel')

    expect(tabList).toBeTruthy()
    expect(registerTab.getAttribute('aria-selected')).toBe('true')
    expect(loginTab.getAttribute('aria-selected')).toBe('false')
    expect(registerTab.getAttribute('aria-controls')).toBe('auth-panel-register')
    expect(panel.getAttribute('id')).toBe('auth-panel-register')
    expect(panel.getAttribute('aria-labelledby')).toBe('auth-tab-register')
  })

  it('supports Arrow/Home/End keyboard navigation between tabs', async () => {
    render(<AuthPage />)

    const registerTab = screen.getByRole('tab', { name: 'Register' })
    const loginTab = screen.getByRole('tab', { name: 'Login' })

    registerTab.focus()
    fireEvent.keyDown(registerTab, { key: 'ArrowRight' })

    await waitFor(() => {
      expect(loginTab.getAttribute('aria-selected')).toBe('true')
      expect(document.activeElement).toBe(loginTab)
      expect(screen.getByRole('tabpanel').getAttribute('id')).toBe('auth-panel-login')
    })

    fireEvent.keyDown(loginTab, { key: 'Home' })

    await waitFor(() => {
      expect(registerTab.getAttribute('aria-selected')).toBe('true')
      expect(document.activeElement).toBe(registerTab)
      expect(screen.getByRole('tabpanel').getAttribute('id')).toBe('auth-panel-register')
    })

    fireEvent.keyDown(registerTab, { key: 'End' })

    await waitFor(() => {
      expect(loginTab.getAttribute('aria-selected')).toBe('true')
      expect(document.activeElement).toBe(loginTab)
      expect(screen.getByRole('tabpanel').getAttribute('id')).toBe('auth-panel-login')
    })
  })
})
