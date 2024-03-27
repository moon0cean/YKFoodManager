import http from "../http-common";

class ContainerDataService {
  getAll() {
    return http.get("/containers");
  }
}

ContainerDataService = new ContainerDataService()
export default ContainerDataService;