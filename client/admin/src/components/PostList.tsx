import React from 'react'
import {Datagrid, List, TextField,} from 'react-admin'

const PostList = (props: any) => {
    return (
        <List {...props}>
            <Datagrid>
                <TextField source='id'/>
            </Datagrid>
        </List>
    )
}

export default PostList