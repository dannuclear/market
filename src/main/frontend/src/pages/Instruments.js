import React, { useMemo, useState } from 'react'
import ServerDataGrid from '../components/ServerDataGrid'
import { IconButton } from '@mui/material'
import axios from '../api/axios'
import OrderDialog from '../components/order/OrderDialog'

const Instruments = () => {

    const [reload, setReload] = useState(false)
    const [order, setOrder] = useState()

    const toggleFavorite = (id) => {
        axios.put(`instruments/${id}/toggleFavorite`)
            .then(resp => setReload(r => !r))
            .catch()
    }

    const buy = (id, figi) => {
        axios.get(`instruments/${figi}/info`)
            .then(infoResp => {
                console.log(infoResp.data);
                axios.get(`instruments/lastPrices?ids=${id}`)
                .then(resp => {
                    const price = resp.data[0]
                    let value = price.price.units + "." + price.price.nano
                    setOrder({ instrumentId: id, price: value, lot: infoResp.data.lot })
                })
                .catch()
            })
            .catch()
    }

    const onBuy = (data) => {
        console.log(data);
        axios.post(`instruments/buy`, {instrumentId: data.instrumentId, accountId: data.account.id, price: data.price, count: data.count})
            .then(resp => (console.log(resp.data), setOrder(null)))
            .catch()
    }

    const columns = useMemo(() => [
        { field: 'figi', headerName: 'FIGI', flex: .1 },
        { field: 'isin', headerName: 'ISIN', flex: .1 },
        { field: 'ticker', headerName: 'Тикер', flex: .1 },
        { field: 'instrumentType', headerName: 'Тип', flex: .1 },
        { field: 'name', headerName: 'Имя', flex: .5 },
        { field: 'lastPrice', headerName: 'Цена', flex: .1 },
        {
            field: 'buy', headerName: '', width: 8, sortable: false, renderCell: p => <IconButton variant='outlined' size='small' onClick={() => buy(p.row.uid, p.row.figi)} title='Favorite'><i className='fa-thin fa-money-bill fa-lg'></i></IconButton>
        },
        {
            field: 'favorite', headerName: '', width: 8, sortable: false, renderCell: p => <IconButton variant='outlined' size='small' onClick={() => toggleFavorite(p.row.uid)} title='Favorite'><i className={`${p.row.favorite ? 'fa-solid' : 'fa-thin'} fa-star fa-lg`}></i></IconButton>
        },
    ])

    return (
        <>
            <ServerDataGrid
                columns={columns}
                path='instruments'
                name='Инструменты'
                //EditDialog={StudyGroupDialog}
                getRowId={data => data.uid}
                reload={reload}
            />

            <OrderDialog
                open={!!order}
                item={order}
                onOk={onBuy}
                onCancel={() => setOrder(null)}
            ></OrderDialog>
        </>
    )
}

export default Instruments