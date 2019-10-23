export class Dashboard {
    uuid: string;
    userName: string;
    dashboardQueries: DashboardQuery[];
}

export class DashboardQuery {
    uuid: string;
    queryUuid: string;
    isChart: string;
}