import React, { useState } from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';

const useStyles = makeStyles((theme) => ({
  modal: {
    display: 'flex',
    alignItems: 'center',
    justifyContent: 'center',
  },
  paper: {
    backgroundColor: theme.palette.background.paper,
    border: '2px solid #000',
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },
}));

const ModalBtn = (props) => {
  const classes = useStyles();
  const { viewSubmitBtn } = props;
  const [open, setOpen] = useState(false);

  const handleOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
    viewSubmitBtn(true);
  };

  return (
    <div>
      <button
        type="button"
        onClick={handleOpen}
        className="label theme-bg text-white f-12 btn-round shadow-2 submit-position"
        sx={{ mt: 3, ml: 1 }}
      >
        submit
      </button>
      <Modal
        aria-labelledby="transition-modal-title"
        aria-describedby="transition-modal-description"
        className={classes.modal}
        open={open}
        onClose={handleClose}
        closeAfterTransition
        BackdropComponent={Backdrop}
        BackdropProps={{
          timeout: 500,
        }}
      >
        <Fade in={open}>
          <div className={classes.paper}>
            <h2 id="transition-modal-title">정말 계약을 진행하시겠습니까?</h2>
            <p id="transition-modal-description">
              계약을 생성하고 상대방이 계약을 승인하면 기록을 지울 수 없습니다.
            </p>
            <p id="transition-modal-description">
              계속 진행하시려면 이 창을 닫고 다시 submit 버튼을 눌러주세요
            </p>
          </div>
        </Fade>
      </Modal>
    </div>
  );
};

export default ModalBtn;
