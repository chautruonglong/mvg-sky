import axios, { AxiosResponse, AxiosError } from 'axios';
// import store from '@/stores/configureStore';

const axiosClient = axios.create({
  baseURL: 'http://api.mvg-sky.com/api',
});

const handleResponseSuccess = (response) => {
  return Promise.resolve(response.data);
};

const handleResponseFailed = (error) => {
  if (error.response?.status === 401) {
    // store.dispatch(logout());
    return Promise.reject('Login session has expired.');
  }

  return Promise.reject(error.response?.data || { message: error.message });
};

axiosClient.interceptors.response.use(
  handleResponseSuccess,
  handleResponseFailed,
);

export default axiosClient;