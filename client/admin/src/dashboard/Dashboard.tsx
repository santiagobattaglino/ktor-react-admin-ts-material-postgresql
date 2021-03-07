import React, {CSSProperties, FC} from 'react';
import {Loading, useQuery} from 'react-admin';
import {List, ListItem, ListItemText} from '@material-ui/core';
import {Link} from 'react-router-dom';

import Welcome from './Welcome';

const styles = {
    flex: {display: 'flex'},
    flexColumn: {display: 'flex', flexDirection: 'column'},
    leftCol: {flex: 1, marginRight: '0.5em'},
    rightCol: {flex: 1, marginLeft: '0.5em'},
    singleCol: {marginTop: '1em', marginBottom: '1em'},
};

const Spacer = () => <span style={{width: '1em'}}/>;
const VerticalSpacer = () => <span style={{height: '1em'}}/>;

const Dashboard: FC = () => {
    return <div>
        <div style={styles.flexColumn as CSSProperties}><Welcome/><VerticalSpacer/><StockByUser/><Spacer/></div>
    </div>
};

interface StockDashboard {
    stock?: Stock[];
    total: number;
}

interface Stock {
    id: number;
    productId: number;
    quantity: number;
}

interface Props {
    stock?: Stock[];
}

const StockByUser = (props: any) => {
    const {data: stockDashboard, total, loading, error} = useQuery({
        type: 'getList',
        resource: 'api/v1/stock/user/2/',
        payload: {pagination: {page: 1, perPage: 10}, sort: {field: 'quantity', order: 'DESC'}}
    });
    if (loading) {
        return <Loading/>;
    }
    if (error) {
        return <p>ERROR</p>;
    }

    return (
        <div>
            <p>User Id 2. Total: {total}</p>
            <List>
                {stockDashboard
                    ? stockDashboard.data.map(item => (
                        <ListItem button to={`api/v1/products/${item.productId}`} component={Link} key={item.id}>
                            <ListItemText primary={`${item.productId}`}/>
                            <ListItemText primary={`${item.quantity}`}/>
                        </ListItem>
                    ))
                    : null}
            </List>
        </div>
    );

    /*return (
        <Card className="card">
            <CardHeader title={'pos.dashboard.pending_orders'} />
            <List dense={true}>
                {data.map(item => (
                    <ListItem
                        key={item.id}
                        button
                        component={Link}
                        to={`/commands/${item.id}`}
                    >
                        
                    </ListItem>
                ))}
            </List>
        </Card>
    );*/
};

export default Dashboard;