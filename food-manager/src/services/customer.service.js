import http from "../http-common";

class CustomerDataService {
  getAll() {
    return http.get("/customers");
  }
}

CustomerDataService = new CustomerDataService();
export default CustomerDataService;