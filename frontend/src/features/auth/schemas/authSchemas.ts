import { z } from 'zod'

export const registerSchema = z.object({
  email: z.email('Email invalide'),
  username: z
    .string()
    .min(3, 'Le pseudo doit contenir au moins 3 caracteres')
    .max(100, 'Le pseudo doit contenir au maximum 100 caracteres'),
  password: z
    .string()
    .min(8, 'Le mot de passe doit contenir au moins 8 caracteres')
    .max(255, 'Le mot de passe doit contenir au maximum 255 caracteres'),
})

export const loginSchema = z.object({
  email: z.email('Email invalide'),
  password: z
    .string()
    .min(8, 'Le mot de passe doit contenir au moins 8 caracteres')
    .max(255, 'Le mot de passe doit contenir au maximum 255 caracteres'),
})

export type RegisterFormValues = z.infer<typeof registerSchema>
export type LoginFormValues = z.infer<typeof loginSchema>
