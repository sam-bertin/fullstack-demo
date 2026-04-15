import apiClient from '../../../shared/lib/apiClient'
import type {
  IApiResponse,
  IAuthUserResponse,
  ILoginRequest,
  ILoginResponse,
  IRegisterRequest,
} from '../types/auth'

export const registerUser = async (
  payload: IRegisterRequest,
): Promise<IApiResponse<IAuthUserResponse>> => {
  const response = await apiClient.post<IApiResponse<IAuthUserResponse>>(
    '/api/v1/auth/register',
    payload,
  )
  return response.data
}

export const loginUser = async (
  payload: ILoginRequest,
): Promise<IApiResponse<ILoginResponse>> => {
  const response = await apiClient.post<IApiResponse<ILoginResponse>>(
    '/api/v1/auth/login',
    payload,
  )
  return response.data
}
