import React, {
    useState,
    useEffect,
    useCallback,
    FC,
    CSSProperties,
} from 'react';
import { useVersion, useDataProvider } from 'react-admin';
import { useMediaQuery, Theme, TextField } from '@material-ui/core';
import { subDays } from 'date-fns';
import { List } from 'react-admin';
import { Datagrid, ReferenceField, DateField, EditButton, DeleteButton } from 'react-admin';

const Dashboard: FC = () => {
    const [state, setState] = useState({});
    const version = useVersion();
    const dataProvider = useDataProvider();

    const fetch = useCallback(async () => {
        const { data } = await dataProvider.getList(
            'api/v1/sales',
            {
                filter: {  },
                sort: { field: 'id', order: 'DESC' },
                pagination: { page: 1, perPage: 100 },
            }
        );
    }, [dataProvider]);

    useEffect(() => {
        fetch();
    }, [version]);

    const {
    } = state;
    return (
        <p></p>
    )
};

export default Dashboard;