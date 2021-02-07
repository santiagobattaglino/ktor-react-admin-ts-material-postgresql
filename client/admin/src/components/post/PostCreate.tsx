import React from 'react'
import { Create, SimpleForm, TextInput, DateInput } from 'react-admin'

const PostCreate = (props: any) => {
    return (
        <Create title='Create a Post' {...props}>
            <SimpleForm>
                <TextInput source='id'/>
                <TextInput source='name'/>
                <TextInput source='countryCode'/>
                <TextInput source='city'/>
                <TextInput source='comments'/>
                <DateInput label='Published' source='publishedAt' />
            </SimpleForm>
        </Create>
    )
}

export default PostCreate