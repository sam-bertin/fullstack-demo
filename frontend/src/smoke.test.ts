import { describe, expect, it } from 'vitest'

describe('frontend ci smoke', () => {
  it('runs unit tests in CI', () => {
    expect(1 + 1).toBe(2)
  })
})
