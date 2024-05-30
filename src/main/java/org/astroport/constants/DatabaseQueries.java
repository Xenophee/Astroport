package org.astroport.constants;

public class DatabaseQueries {

    public static final String QUERY_GET_NEXT_AVAILABLE_DOCK = "select min(DOCK_NUMBER) from docks where AVAILABLE = true and TYPE = ?";
    public static final String QUERY_UPDATE_DOCK_AVAILABILITY = "update docks set available = ? where DOCK_NUMBER = ?";
    public static final String QUERY_INSERT_NEW_TICKET = "insert into tickets(DOCK_NUMBER, SHIP_REG_NUMBER, PRICE, IN_TIME, OUT_TIME) values(?,?,?,?,?)";
    public static final String QUERY_UPDATE_TICKET_PRICE_AND_OUT_TIME = "update tickets set PRICE = ?, OUT_TIME = ? where ID = ?";
    public static final String QUERY_GET_TICKET_BY_SHIP_REG_NUMBER = "select t.DOCK_NUMBER, t.ID, t.PRICE, t.IN_TIME, t.OUT_TIME, d.TYPE from tickets t,docks d where d.dock_number = t.dock_number and t.SHIP_REG_NUMBER = ? order by t.IN_TIME desc limit 1";
    public static final String QUERY_GET_TICKET_COUNT_BY_SHIP_REG_NUMBER = "select count(SHIP_REG_NUMBER) from tickets where SHIP_REG_NUMBER = ?";

}