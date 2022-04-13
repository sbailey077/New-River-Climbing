package com.techelevator.dao;

import com.techelevator.model.Route;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcRouteDao implements RouteDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcRouteDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Route> getRoutesByCragName(String cragName) {
        List<Route> routesByCrags = new ArrayList<>();
        String sql = "SELECT routes.route_id, routes.name, routes.description, routes.grade, routes.height, routes.rating, routes.sport_trad, routes.has_anchors, routes.crag_id " +
                "FROM routes " +
                "JOIN crags ON routes.crag_id = crags.crag_id " +
                "WHERE crags.name = ?";

        SqlRowSet rows = jdbcTemplate.queryForRowSet(sql, cragName);

        while(rows.next()) {
            Route route = mapRowToRoute(rows);
            routesByCrags.add(route);
        }

        return routesByCrags;
    }

    private Route mapRowToRoute(SqlRowSet row) {
        Route route = new Route();
        route.setRouteId(row.getInt("route_id"));
        route.setRouteName(row.getString("name"));
        route.setDescription(row.getString("description"));
        route.setGrade(row.getString("grade"));
        route.setHeight(row.getInt("height"));
        route.setRating(row.getInt("rating"));
        route.setSportTrad(row.getString("sport_trad"));
        route.setHasAnchors(row.getBoolean("has_anchors"));
        route.setCragId(row.getInt("crag_id"));

        return route;
    }

}