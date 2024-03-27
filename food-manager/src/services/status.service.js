import http from "../http-common";

class StatusDataService {
  getAll() {
    return http.get("/statuses");
  }
}

StatusDataService = new StatusDataService();
export default StatusDataService;