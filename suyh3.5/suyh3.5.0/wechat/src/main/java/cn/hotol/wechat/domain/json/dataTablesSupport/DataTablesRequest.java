package cn.hotol.wechat.domain.json.dataTablesSupport;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * {
 * "sEcho":1,
 * "iColumns":7,
 * "sColumns":"",
 * "iDisplayStart":0,
 * "iDisplayLength":10,
 * "amDataProp":[0,1,2,3,4,5,6],
 * "sSearch":"",
 * "bRegex":false,
 * "asSearch":["","","","","","",""],
 * "abRegex":[false,false,false,false,false,false,false],
 * "abSearchable":[true,true,true,true,true,true,true],
 * "iSortingCols":1,
 * "aiSortCol":[0],
 * "asSortDir":["asc"],
 * "abSortable":[true,true,true,true,true,true,true]
 * }
 *
 * @author inder
 *         <p/>
 *         login: Hill Pan
 *         Date: 12/20/11
 *         Time: 9:59 AM
 */
public class DataTablesRequest implements Serializable {

    @JsonProperty(value = "sEcho")
    private String echo;

    @JsonProperty(value = "iColumns")
    private int numColumns;

    @JsonProperty(value = "sColumns")
    private String columns;

    @JsonProperty(value = "iDisplayStart")
    private int displayStart;

    @JsonProperty(value = "iDisplayLength")
    private int displayLength;

    //has to be revisited for Object type dataProps.
    @JsonProperty(value = "amDataProp")
    private List<String> dataProp;

    @JsonProperty(value = "sSearch")
    private String searchQuery;

    @JsonProperty(value = "asSearch")
    private List<String> columnSearches;

    @JsonProperty(value = "bRegex")
    private boolean hasRegex;

    @JsonProperty(value = "abRegex")
    private List<Boolean> regexColumns;

    @JsonProperty(value = "abSearchable")
    private List<Boolean> searchColumns;

    @JsonProperty(value = "iSortingCols")
    private int sortingCols;

    @JsonProperty(value = "aiSortCol")
    private List<Integer> sortedColumns;

    @JsonProperty(value = "asSortDir")
    private List<String> sortDirections;

    @JsonProperty(value = "abSortable")
    private List<Boolean> sortableColumns;

    public String getEcho() {
        return echo;
    }

    public void setEcho(String echo) {
        this.echo = echo;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public void setNumColumns(int numColumns) {
        this.numColumns = numColumns;
    }

    public String getColumns() {
        return columns;
    }

    public void setColumns(String columns) {
        this.columns = columns;
    }

    public int getDisplayStart() {
        return displayStart;
    }

    public void setDisplayStart(int displayStart) {
        this.displayStart = displayStart;
    }

    public int getDisplayLength() {
        return displayLength;
    }

    public void setDisplayLength(int displayLength) {
        this.displayLength = displayLength;
    }

    public List<String> getDataProp() {
        return dataProp;
    }

    public void setDataProp(List<String> dataProp) {
        this.dataProp = dataProp;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }

    public List<String> getColumnSearches() {
        return columnSearches;
    }

    public int getPageNo() {
        return displayStart / displayLength + 1;
    }

    public int getPageSize() {
        return displayLength;
    }

    public void setColumnSearches(List<String> columnSearches) {
        this.columnSearches = columnSearches;
    }

    public boolean isHasRegex() {
        return hasRegex;
    }

    public void setHasRegex(boolean hasRegex) {
        this.hasRegex = hasRegex;
    }

    public List<Boolean> getRegexColumns() {
        return regexColumns;
    }

    public void setRegexColumns(List<Boolean> regexColumns) {
        this.regexColumns = regexColumns;
    }

    public List<Boolean> getSearchColumns() {
        return searchColumns;
    }

    public void setSearchColumns(List<Boolean> searchColumns) {
        this.searchColumns = searchColumns;
    }

    public int getSortingCols() {
        return sortingCols;
    }

    public void setSortingCols(int sortingCols) {
        this.sortingCols = sortingCols;
    }

    public List<Integer> getSortedColumns() {
        return sortedColumns;
    }

    public List<String> getSortedColumnNames() {

        List<String> sortedColumnNames = new ArrayList<String>();

        for (Integer sortedColumn : sortedColumns) {
            sortedColumnNames.add(dataProp.get(sortedColumn));
        }

        return sortedColumnNames;
    }

/*
    public List<PropertyOrder> getPropertyOrderList() {

        List<PropertyOrder> propertyOrderList = new ArrayList<PropertyOrder>();

        for (int i = 0; i < sortedColumns.size(); i ++) {
            if (sortDirections.get(i).equalsIgnoreCase("asc")) {
                propertyOrderList.add(PropertyOrder.asc(dataProp.get(sortedColumns.get(i))));
            } else {
                propertyOrderList.add(PropertyOrder.desc(dataProp.get(sortedColumns.get(i))));
            }
        }
        return propertyOrderList;
    }

*/
    public void setSortedColumns(List<Integer> sortedColumns) {
        this.sortedColumns = sortedColumns;
    }

    public List<String> getSortDirections() {
        return sortDirections;
    }

    public void setSortDirections(List<String> sortDirections) {
        this.sortDirections = sortDirections;
    }

    public List<Boolean> getSortableColumns() {
        return sortableColumns;
    }

    public void setSortableColumns(List<Boolean> sortableColumns) {
        this.sortableColumns = sortableColumns;
    }
}
