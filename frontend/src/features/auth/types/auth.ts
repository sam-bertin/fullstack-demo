export interface IApiResponse<T> {
  success: boolean
  message: string
  data: T | null
  errorCode: string | null
}

export interface IRegisterRequest {
  email: string
  username: string
  password: string
}

export interface ILoginRequest {
  email: string
  password: string
}

export interface IAuthUserResponse {
  id: number
  email: string
  username: string
  createdAt: string
}

export interface ILoginResponse {
  user: IAuthUserResponse
  authMode: string
}
