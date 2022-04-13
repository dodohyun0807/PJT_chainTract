import React from 'react';
import Image from 'next/image';
import PropTypes from 'prop-types';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TablePagination from '@material-ui/core/TablePagination';
import TableRow from '@material-ui/core/TableRow';
import TableSortLabel from '@material-ui/core/TableSortLabel';
import Paper from '@material-ui/core/Paper';
import { useRouter } from 'next/router';
import Styled from './styled';
import { useQuery } from 'react-query';
import { apiInstance } from '@/libs/axios';
import { styled } from '@mui/material/styles';
import Box from '@mui/material/Box';
import Grid from '@mui/material/Grid';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import image__loading from '/public/Spinner-1s-200px.svg';

const Item = styled(Paper)(({ theme }) => ({
  backgroundColor: theme.palette.mode === 'dark' ? '#1A2027' : '#fff',
  ...theme.typography.body2,
  padding: theme.spacing(1),
  textAlign: 'center',
  color: theme.palette.text.secondary,
}));
const bull = (
  <Box component="span" sx={{ display: 'inline-block', mx: '2px', transform: 'scale(0.8)' }}>
    •
  </Box>
);

function createData(id, name, createdDate, establishedDate, counterpart) {
  return { id, name, createdDate, establishedDate, counterpart };
}

function descendingComparator(a, b, orderBy) {
  if (b[orderBy] < a[orderBy]) {
    return -1;
  }
  if (b[orderBy] > a[orderBy]) {
    return 1;
  }
  return 0;
}

function getComparator(order, orderBy) {
  return order === 'desc'
    ? (a, b) => descendingComparator(a, b, orderBy)
    : (a, b) => -descendingComparator(a, b, orderBy);
}

function stableSort(array, comparator) {
  const stabilizedThis = array.map((el, index) => [el, index]);
  stabilizedThis.sort((a, b) => {
    const order = comparator(a[0], b[0]);
    if (order !== 0) return order;
    return a[1] - b[1];
  });
  return stabilizedThis.map((el) => el[0]);
}

const headCells = [
  { id: 'id', disablePadding: false, label: 'id' },
  { id: 'contractName', disablePadding: false, label: 'contractName' },
  { id: 'createdDate', disablePadding: false, label: 'createdDate' },
  { id: 'establishedDate', disablePadding: false, label: 'establishedDate' },
  { id: 'counterpart', disablePadding: false, label: 'counterpart' },
];

function EnhancedTableHead(props) {
  const { classes, order, orderBy, onRequestSort } = props;
  const createSortHandler = (property) => (event) => {
    onRequestSort(event, property);
  };

  return (
    <TableHead>
      <TableRow>
        {headCells.map((headCell) => (
          <TableCell
            key={headCell.id}
            align={headCell.numeric ? 'right' : 'left'}
            padding={headCell.disablePadding ? 'none' : 'normal'}
            sortDirection={orderBy === headCell.id ? order : false}
          >
            <TableSortLabel
              active={orderBy === headCell.id}
              direction={orderBy === headCell.id ? order : 'asc'}
              onClick={createSortHandler(headCell.id)}
            >
              {headCell.label}
              {orderBy === headCell.id ? (
                <span className={classes.visuallyHidden}>
                  {order === 'desc' ? 'sorted descending' : 'sorted ascending'}
                </span>
              ) : null}
            </TableSortLabel>
          </TableCell>
        ))}
      </TableRow>
    </TableHead>
  );
}

EnhancedTableHead.propTypes = {
  classes: PropTypes.object.isRequired,
  onRequestSort: PropTypes.func.isRequired,
  order: PropTypes.oneOf(['asc', 'desc']).isRequired,
  orderBy: PropTypes.string.isRequired,
  rowCount: PropTypes.number.isRequired,
};

const CompleteBlock = () => {
  const api = apiInstance();
  const router = useRouter();
  const classes = Styled.useStyles();
  const [order, setOrder] = React.useState('asc');
  const [orderBy, setOrderBy] = React.useState('calories');
  const [page, setPage] = React.useState(0);
  const [rowsPerPage, setRowsPerPage] = React.useState(8);
  const rows = [];
  let userInfo = '';
  if (typeof window !== 'undefined' && window.sessionStorage) {
    userInfo = sessionStorage.getItem('chainTractLoginInfo');
  }

  const { isLoading, error, isSuccess, data } = useQuery('completeBlockData', () =>
    api.get('/contracts/block'),
  );
  if (isLoading)
    return (
      <Styled.ContentContainer>
        <Typography variant="h5" gutterBottom>
          Loading...
        </Typography>
        <Image src={image__loading} alt="image__loading" className="image__loading" />
      </Styled.ContentContainer>
    );
  if (error) return 'An error has occurred: ' + error.message;
  if (isSuccess) {
    data.data.response.map((contract) => {
      rows.push(
        createData(
          contract.id,
          contract.name,
          contract.createdDate,
          contract.establishedDate,
          contract.participantEmails,
        ),
      );
    });
  }

  const handleRequestSort = (property) => {
    const isAsc = orderBy === property && order === 'asc';
    setOrder(isAsc ? 'desc' : 'asc');
    setOrderBy(property);
  };

  const handleClick = (e, id) => {
    e.preventDefault();
    router.push(`/contractdetail/${id}`);
  };

  const handleChangePage = (newPage) => {
    setPage(newPage);
  };

  const handleChangeRowsPerPage = (e) => {
    setRowsPerPage(parseInt(e.target.value, 10));
    setPage(0);
  };

  return (
    <div className={classes.root}>
      {rows.length > 0 ? (
        <Paper className={classes.paper}>
          <Box>
            <Grid container spacing={0}>
              {stableSort(rows, getComparator(order, orderBy))
                .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
                .map((row, index) => {
                  const labelId = `enhanced-table-checkbox-${index}`;
                  return (
                    <Grid
                      item
                      xs={3}
                      key={row.id}
                      hover="true"
                      onClick={(event) => handleClick(event, row.id)}
                      tabIndex={-1}
                      className={classes.tableRow}
                      p={3}
                    >
                      <Item>
                        <CardContent>
                          <div className="color-test">
                            <Typography sx={{ fontSize: 14 }} gutterBottom>
                              <br />
                              {row.id}
                              <br />
                            </Typography>
                            <Typography variant="h5" component="div">
                              {bull} {row.name} {bull}
                            </Typography>

                            <Typography variant="body2">
                              <br />
                              생성일 : {row.createdDate.slice(0, 10)}
                              <br />
                              체결일 : {row.establishedDate.slice(0, 10)}
                              <br />
                            </Typography>

                            <Typography sx={{ mb: 1.5 }}>
                              <br />
                              {row.counterpart}
                              <br />
                            </Typography>
                          </div>
                        </CardContent>
                        <CardActions>
                          <Button size="small" className="theme-bg3 text-white btn-round">
                            자세히
                          </Button>
                        </CardActions>
                      </Item>
                    </Grid>
                  );
                })}
            </Grid>
          </Box>
          <br />
          <br />

          <TablePagination
            rowsPerPageOptions={[8, 12, 16, 20]}
            component="div"
            count={rows.length}
            rowsPerPage={rowsPerPage}
            page={page}
            onPageChange={handleChangePage}
            onRowsPerPageChange={handleChangeRowsPerPage}
          />

          <br />
          <br />
        </Paper>
      ) : (
        <Styled.noneBox>계약이 없습니다</Styled.noneBox>
      )}
    </div>
  );
};

export default CompleteBlock;
