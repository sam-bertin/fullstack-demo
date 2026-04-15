import { expect, test } from '@playwright/test'

test.describe('Auth UI E2E', () => {
  test('register then login succeeds', async ({ page }) => {
    const suffix = Date.now()
    const email = `e2e.${suffix}@example.com`
    const username = `e2euser${suffix}`
    const password = 'testpass1'

    await page.goto('/')

    await page.getByRole('textbox', { name: 'Email' }).fill(email)
    await page.getByRole('textbox', { name: 'Username' }).fill(username)
    await page.getByLabel('Password').fill(password)
    await page.getByRole('button', { name: 'Créer un compte' }).click()

    await expect(page.locator('.feedback')).toContainText('Register OK:')

    await page.getByRole('tab', { name: 'Login', exact: true }).click()
    await page.getByRole('textbox', { name: 'Email' }).fill(email)
    await page.getByLabel('Password').fill(password)
    await page.getByRole('button', { name: 'Se connecter' }).click()

    await expect(page.locator('.feedback')).toContainText('Login OK:')
  })

  test('login invalid credentials returns API error feedback', async ({ page }) => {
    await page.goto('/')

    await page.getByRole('tab', { name: 'Login', exact: true }).click()
    await page.getByRole('textbox', { name: 'Email' }).fill('unknown@example.com')
    await page.getByLabel('Password').fill('testpass2')
    await page.getByRole('button', { name: 'Se connecter' }).click()

    await expect(page.getByText('INVALID_CREDENTIALS')).toBeVisible()
  })
})
