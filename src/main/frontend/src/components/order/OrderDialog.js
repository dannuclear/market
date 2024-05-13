import React from 'react'
import AgreeDisagreeDialog from '../AgreeDisagreeDialog'
import OrderForm from './OrderForm'

export default function OrderDialog({
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
            form='order-form'
            inProgress={inProgress}
            content={
                <OrderForm formId='order-form' order={item} onSave={onSave} />
            } />
    )
}