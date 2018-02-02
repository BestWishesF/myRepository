package cn.hotol.app.bean.dto.location;

/**
 * Created by Administrator on 2017-03-25.
 */
public class ChangeLngLonDto {

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

        private Integer precise;

        private Integer confidence;

        private String level;

        public LocationDto getLocation() {
            return location;
        }

        public void setLocation(LocationDto location) {
            this.location = location;
        }

        public Integer getPrecise() {
            return precise;
        }

        public void setPrecise(Integer precise) {
            this.precise = precise;
        }

        public Integer getConfidence() {
            return confidence;
        }

        public void setConfidence(Integer confidence) {
            this.confidence = confidence;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }
    }

}
