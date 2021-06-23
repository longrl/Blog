import axios from "axios";

const http = axios.create({
  baseURL: "http://autwind.cn:5000/v1",
  timeout: 120000
});

export default http;
