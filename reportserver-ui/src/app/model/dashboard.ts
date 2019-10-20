class Dashboard{
    uuid: string;
    userName: string;
    dashboardQueries: DashboardQuery[];
}

class DashboardQuery {
    uuid: string;
    queryUuid: string;
    isChart: string;
}