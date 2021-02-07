import React from 'react'
import {Datagrid, DeleteButton, EditButton, List, TextField, DateField} from 'react-admin'

const PostList = (props: any) => {
    return (
        <List {...props} perPage={25}>
            <Datagrid>
                <TextField source='id'/>
                <TextField source='name'/>
                <TextField source='countryCode'/>
                <TextField source='city'/>
                <TextField source='comments'/>
                <DateField source='dateCreated'/>
                <EditButton basePath='departments'/>
                <DeleteButton basePath='departments'/>
            </Datagrid>
        </List>
    )
}

export default PostList