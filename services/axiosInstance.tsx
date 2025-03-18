import axios from "axios";
import { error } from "console";
import { useRouter } from "next/navigation";

export const safeAxios = () => {
  const router = useRouter();

  const axiosInstance = axios.create();

  axiosInstance.interceptors.request.use(
    (config) => {
      const token = localStorage.getItem("jwtToken");
      if (token) {
        config.headers["Authorization"] = token;
      }
      return config;
    },

    (err) => {
      return Promise.reject(err);
    }
  );

  axiosInstance.interceptors.response.use(
    (response) => {
      return response;
    },

    (err) => {
        console.log(err.response.status);
      if (err.response.status === 401) {
        console.log("Unauthorized user");
        router.replace("/");
      }

      return Promise.reject(err);
    }
  );

  return axiosInstance;
};
