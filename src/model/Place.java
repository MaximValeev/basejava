package model;

import java.util.ArrayList;
import java.util.List;

public class Place {
    private Link homePage;
    private List<WorkPeriod> workPeriods;

    public Place(String name, String url, List<WorkPeriod> workPeriods) {
        this.homePage = new Link(name, url);
        this.workPeriods = workPeriods;
    }

    public void addBusyPeriod(WorkPeriod workPeriod) {
        this.workPeriods.add(workPeriod);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(homePage).append('\n');
        for (WorkPeriod wp : workPeriods) {
            sb.append(wp.toString()).append('\n');
        }
        return sb.toString();
    }


}


