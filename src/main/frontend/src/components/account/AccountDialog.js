import React from 'react'
import AccountForm from './AccountForm'
import AgreeDisagreeDialog from '../AgreeDisagreeDialog'

export default function AccountDialog({
    item,
    open = false,
    title = 'Счет',
    onOk = (data) => console.log(data),
    onCancel = () => console.log('Отмена'),
    minHeight = 50,
    inProgress = false
}) {

    const onSave = (data) => {
        onOk({ ...data })
    }

    return (
        <AgreeDisagreeDialog
            title={title}
            open={open}
            onDisagree={onCancel}
            minHeight={minHeight}
            form='account-form'
            inProgress={inProgress}
            content={
                <AccountForm formId='account-form' grantType={item} onSave={onSave} />
            } />
    )
}