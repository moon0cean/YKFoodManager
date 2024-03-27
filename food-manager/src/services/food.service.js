import http from "../http-common";

class FoodDataService {
  getAll() {
    return http.get("/foods");
  }

  get(id) {
    return http.get(`/foods/${id}`);
  }

  create(data) {
    return http.post("/foods", data);
  }

  update(id, data) {
    return http.put(`/foods/${id}`, data);
  }

  delete(id) {
    return http.delete(`/foods/${id}`);
  }

  deleteAll() {
    return http.delete(`/foods`);
  }

}

FoodDataService = new FoodDataService();
export default FoodDataService;