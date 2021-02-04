import React from 'react'
import {Edit, SimpleForm, TextInput, DateInput} from 'react-admin'

const PostEdit = (props: any) => {
    return (
        <Edit title='Edit Post' {...props}>
            <SimpleForm>
                <TextInput source='id'/>
                <TextInput source='name'/>
                <TextInput source='countryCode'/>
                <TextInput source='city'/>
                <TextInput source='comments'/>
                <DateInput label='Published' source='publishedAt' />
            </SimpleForm>
        </Edit>
    )
}

export default PostEdit
