import axios from 'axios';

const apiRequest = axios.create({
    baseURL: 'http://mvg-sky.com:8002/api',
});

const handleResponseSuccess = (response) => {
    return Promise.resolve(response.data);
};

const handleResponseFailed = (error) => {
    return Promise.reject(error.response?.data || error.message);
};

apiRequest.interceptors.response.use(
    handleResponseSuccess,
    handleResponseFailed,
);

export default apiRequest;