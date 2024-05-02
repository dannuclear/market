import axios from 'axios'

const instance = axios.create({
    baseURL: `http://${document.location.hostname}:${document.location.port == 3000?8080:document.location.port}${process.env.PUBLIC_URL}/api/v1`,
    timeout: 5000,
    withCredentials: true
})

export default instance;