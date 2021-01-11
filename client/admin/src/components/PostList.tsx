import React from 'react'
import {Datagrid, DeleteButton, EditButton, List, TextField} from 'react-admin'

const PostList = (props: any) => {
    return (
        <List {...props}>
            <Datagrid>
                <TextField source='id'/>
                <EditButton basePath='departments'/>
                <DeleteButton basePath='departments'/>
            </Datagrid>
        </List>
    )
}

export default PostList