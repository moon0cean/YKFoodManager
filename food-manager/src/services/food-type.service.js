import http from "../http-common";

class FoodTypeDataService {
  getAll() {
    console.log("FoodTypeDataService")
    return http.get("/foodtypes");
  }
}

FoodTypeDataService = new FoodTypeDataService();
export default FoodTypeDataService;