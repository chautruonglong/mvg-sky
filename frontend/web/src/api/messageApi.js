import axiosClient from "./axiosClient"

const messageApi = {
    get: async (roomId) => {
        const url = `/messages?roomId=${roomId}`
        return await axiosClient.get(url)
    }
}

export default messageApi