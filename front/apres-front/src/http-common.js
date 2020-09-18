import axios from "axios";

export default axios.create({
    ///PocetniREST/rest
    baseURL: "/",
    headers: {
        "Content-type": "application/json",
    }
});