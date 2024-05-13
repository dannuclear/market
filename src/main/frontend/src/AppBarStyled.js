// import {faDroplet, faGear, faHouse, faHouseCrack, faScaleUnbalanced, faShuffle, faVirus} from "@fortawesome/free-solid-svg-icons";
// import {faAward, faBooks, faBringFront, faBuilding, faBullseye, faCube, faEyeDropper, faLayerGroup, faPrintSearch, faRuler, faScrewdriver, faToolbox, faUser} from "@fortawesome/pro-light-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { AppBar, Avatar, Box, Button, Icon, IconButton, Link, ListItemIcon, ListItemText, Menu, MenuItem, Toolbar, Typography } from "@mui/material";
import { useContext, useState } from "react";
import { useNavigate } from "react-router-dom";
// import {AuthContext} from "./AuthContext";
import { BASE_URL } from "./api/axios";

const items = [
    {
        name: 'Инструменты',
        icon: 'fa-duotone fa-children',
        navigate: 'instruments'
    },

    {
        name: 'Счета',
        icon: 'fa-duotone fa-children',
        navigate: 'accounts'
    },
    // {
    //     name: 'Выплаты',
    //     icon: 'fa-solid fa-money-bill-transfer',
    //     items: [
    //         {
    //             name: 'Расчет начислений',
    //             icon: 'fa-regular fa-calculator',
    //             navigate: 'calculations'
    //         },
    //         {
    //             name: 'Ведомости',
    //             icon: 'fa-light fa-clipboard-list',
    //             navigate: 'payments'
    //         }
    //     ]
    // },
    // {
    //     name: 'Справочники',
    //     icon: 'fa-regular fa-layer-group',
    //     items: [
    //         {
    //             name: 'Виды стипендий',
    //             icon: 'fa-regular fa-coin',
    //             navigate: 'grantTypes'
    //         },
    //         {
    //             name: 'Группы',
    //             icon: 'fa-regular fa-user-group',
    //             navigate: 'studyGroups'
    //         },
    //         {
    //             name: 'Платежные агенты',
    //             icon: 'fa-regular fa-building-columns',
    //             navigate: 'paymentAgents'
    //         },
    //     ]
    // },
]

const AppBarStyled = () => {
    const navigate = useNavigate()
    // const user = useContext(AuthContext)
    const [anchorEl, setAnchorEl] = useState(null);
    const [anchorUserEl, setAnchorUserEl] = useState(null);
    const open = Boolean(anchorEl);
    const openUser = Boolean(anchorUserEl);
    const [openedItemIdx, setOpenedItemIdx] = useState(0)

    const handleClick = (event, idx) => {
        setOpenedItemIdx(idx)
        setAnchorEl(event.currentTarget);
    };

    const handleUserClick = event => {
        setAnchorUserEl(event.currentTarget);
    };

    const handleClose = () => {
        setAnchorEl(null);
    };

    const handleUserClose = () => {
        setAnchorUserEl(null);
    };

    const logout = () => {

    }

    return (
        <AppBar position='static' sx={{ mb: 1 }}>
            <Toolbar>
                <IconButton
                    size="large"
                    edge="start"
                    color="inherit"
                    aria-label="menu"
                    sx={{ mr: 2 }}
                >
                    {/* <FontAwesomeIcon icon={faVirus}/> */}
                    <i className="fa-regular fa-graduation-cap"></i>
                </IconButton>

                <Box sx={{ flexGrow: 1, display: { xs: 'none', md: 'flex' } }}>
                    {
                        items.map((item, idx) => <Button key={idx} startIcon={<i className={item.icon} />} sx={{
                            color: 'white',
                            '&:hover': { color: 'black' }
                        }} onClick={e => item.navigate ? navigate(item.navigate) : handleClick(e, idx)}>{item.name}</Button>)
                    }

                    {(openedItemIdx != null) && (items[openedItemIdx].items) && <Menu
                        id='basic-menu'
                        anchorEl={anchorEl}
                        open={open}
                        onClose={handleClose}
                        MenuListProps={{
                            'aria-labelledby': 'basic-button',
                        }}
                    >
                        {
                            items[openedItemIdx].items.map((item, idx) => <MenuItem key={idx} onClick={e => (handleClose(), navigate(item.navigate))} sx={{ py: 0.3 }}>
                                <ListItemIcon sx={{ fontSize: 20 }}>
                                    {/* <FontAwesomeIcon icon={item.icon}/> */}
                                    <i className={item.icon} />
                                </ListItemIcon>
                                <ListItemText>{item.name}</ListItemText>
                            </MenuItem>)
                        }
                    </Menu>}
                </Box>

                <Box sx={{ flexGrow: 0 }}>
                    {/* <Typography sx={{display: 'inline', pr: 1, ml: 1}}>{user?.username}</Typography> */}

                    {/* <IconButton sx={{p: 0}} onClick={handleUserClick}>
                        <Avatar alt={user?.username}/>
                    </IconButton> */}

                    <Menu
                        sx={{ mt: '45px' }}
                        id="menu-appbar"
                        anchorEl={anchorUserEl}
                        anchorOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        keepMounted
                        transformOrigin={{
                            vertical: 'top',
                            horizontal: 'right',
                        }}
                        open={openUser}
                        onClose={handleUserClose}
                    >
                        <MenuItem component={Link} href='../logout' sx={{ py: 0.3 }}>Выйти</MenuItem>
                    </Menu>
                </Box>
            </Toolbar>
        </AppBar>
    )
}

export default AppBarStyled;