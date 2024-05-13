import { Grid } from '@mui/material'
import React, { useEffect, useState } from 'react'
import axios from '../../api/axios'
import AgreeDisagreeDialog from '../AgreeDisagreeDialog'
import PortfolioInfo from '../portfolio/PortfolioInfo'

export default function InfoDialog({
    item,
    open = false,
    title = 'Назначение',
    onOk = (data) => console.log(data),
    onCancel = () => console.log('Отмена'),
    minHeight = 100,
    inProgress = false
}) {

    const [portfolio, setPortfolio] = useState()

    useEffect(() => {
        if (!!item)
            axios.get(`accounts/${item.id}/portfolio`)
                .then(res => {
                    setPortfolio({accountId: item.id, ...res?.data})
                })
                .catch(err => {
                    console.log(err);
                })
    }, [item])

    const _open = !!item

    return (
        <AgreeDisagreeDialog
            title={title}
            open={_open}
            onDisagree={onCancel}
            onAgree={onOk}
            minHeight={minHeight}
            inProgress={inProgress}
            maxWidth='sm'
            content={
                <PortfolioInfo portfolio={portfolio} />
            } />
    )
}