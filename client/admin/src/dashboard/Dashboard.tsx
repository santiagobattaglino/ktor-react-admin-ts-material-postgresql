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


/*

import * as React from "react";
import { useQuery } from "react-admin";
import { List, Datagrid, TextField, ReferenceField, DateField, EditButton, DeleteButton } from "react-admin";

const Total = props => {
    const {
        basePath, // deduced from the location, useful for action buttons
        defaultTitle, // the translated title based on the resource, e.g. 'Post #123'
        loaded, // boolean that is false until the record is available
        loading, // boolean that is true on mount, and false once the record was fetched
        record, // record fetched via dataProvider.getOne() based on the id from the location
        resource, // the resource name, deduced from the location. e.g. 'posts'
        version, // integer used by the refresh feature
    } = useShowController(props);
    return (
        <div>
            <h1>{defaultTitle}</h1>
            {cloneElement(props.children, {
                basePath,
                record,
                resource,
                version,
            })}
        </div>
    );
}

const PostShow = props => (
    <Total {...props}>
        <SimpleShowLayout>
            ...
        </SimpleShowLayout>
    </Total>
)
*/

/*export default () => (
    <Card>
        <Title title="Welcome to the administration" />
        <CardContent>Lorem ipsum sic dolor amet...</CardContent>
    </Card>
);*/

const UseQuerySales = (props: any) => {
    const { data, total, loading, error } = useQuery({
        type: 'getList',
        resource: 'api/v1/sales',
        payload: { pagination: { page: 1, perPage: 10 }, sort: { field: 'quantity', order: 'DESC' } }
    });
    /*if (loading) {
        return <Loading/>;
    }
    if (error) {
        return <p>ERROR</p>;
    }*/

    /*return (
        <List title="Sales" {...props} perPage={25} sort={{ field: 'id', order: 'DESC' }}>
            <Datagrid>
                <TextField source='notes' label='Notas' />

                <ReferenceField label="De Lugar de Venta / Vendedor" source="sellerId" reference="api/v1/users" sortBy="firstName">
                    <TextField source="firstName" />
                </ReferenceField>

                <ReferenceField label="A Cliente" source="clientId" reference="api/v1/users" sortBy="firstName">
                    <TextField source="firstName" />
                </ReferenceField>

                <ReferenceField label="Método de Pago" source="paymentMethodId" reference="api/v1/options/type/paymentMethod">
                    <TextField source="name" />
                </ReferenceField>

                <ReferenceField label="Precio" source="priceId" reference="api/v1/options/type/price">
                    <TextField source="name" />
                </ReferenceField>

                <DateField source="dateCreated" label='Fecha' />

                <EditButton basePath='sales' undoable={true} />
                <DeleteButton basePath='sales' undoable={true} />
            </Datagrid>
        </List>
    );*/

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

//export default UseQuerySales;
