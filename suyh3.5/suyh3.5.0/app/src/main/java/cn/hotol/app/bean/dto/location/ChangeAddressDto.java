package cn.hotol.app.bean.dto.location;

/**
 * Created by Administrator on 2017-03-25.
 */
public class ChangeAddressDto {

    private String status;

    private Result result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public static class Result {

        private LocationDto location;

        private String formatted_address;

        private String business;

        private AddressDto addressComponent;

        private Object[] pois;

        private Object[] poiRegions;

        private String sematic_description;

        private Integer cityCode;

        public LocationDto getLocation() {
            return location;
        }

        public void setLocation(LocationDto location) {
            this.location = location;
        }

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public String getBusiness() {
            return business;
        }

        public void setBusiness(String business) {
            this.business = business;
        }

        public AddressDto getAddressComponent() {
            return addressComponent;
        }

        public void setAddressComponent(AddressDto addressComponent) {
            this.addressComponent = addressComponent;
        }

        public Object[] getPois() {
            return pois;
        }

        public void setPois(Object[] pois) {
            this.pois = pois;
        }

        public Object[] getPoiRegions() {
            return poiRegions;
        }

        public void setPoiRegions(Object[] poiRegions) {
            this.poiRegions = poiRegions;
        }

        public String getSematic_description() {
            return sematic_description;
        }

        public void setSematic_description(String sematic_description) {
            this.sematic_description = sematic_description;
        }

        public Integer getCityCode() {
            return cityCode;
        }

        public void setCityCode(Integer cityCode) {
            this.cityCode = cityCode;
        }
    }

}
