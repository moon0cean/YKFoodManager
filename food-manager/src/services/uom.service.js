import http from "../http-common";

class UomDataService {
  getAll() {
    return http.get("/uoms");
  }
}

UomDataService = new UomDataService();
export default UomDataService;