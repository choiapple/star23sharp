import { AxiosResponse } from 'axios'
import AuthAxios from '../../storage/AuthAxios'

const DOMAIN = process.env.NEXT_PUBLIC_API_URL || ''

const friendListGet = async (): Promise<any> => {
  try {
    const response: AxiosResponse = await AuthAxios({
      method: 'get',
      url: `${DOMAIN}/api/letters/today`,
    })
    return response
  } catch (error) {
    console.error(error)
    throw error
  }
}

export default friendListGet
