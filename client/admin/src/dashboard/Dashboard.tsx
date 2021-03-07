import React, {CSSProperties, FC,} from 'react';
import {Loading, useQuery} from 'react-admin';

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

interface Stock {
    id: number;
    productId: number;
    quantity: number;
}

interface Props {
    stock?: Stock[];
}

const StockByUser: FC<Props> = ({stock = []}) => {
    const {data, total, loading, error} = useQuery({
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

    return (<ul>
        {data.map(item => (
            <li key={item.id}>{item.quantity}</li>
        ))}
    </ul>);
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