import React, { useRef } from 'react';
import { Transition, TransitionGroup } from 'react-transition-group';
import { useRouter } from 'next/router';

const TIMEOUT = 180;
const getTransitionStyles = {
  entering: {
    position: `absolute`,
    opacity: 0,
  },
  entered: {
    transition: `opacity ${TIMEOUT}ms ease-in-out, transform ${TIMEOUT}ms ease-in-out`,
    opacity: 1,
  },
  exiting: {
    transition: `opacity ${TIMEOUT}ms ease-in-out, transform ${TIMEOUT}ms ease-in-out`,
    opacity: 0,
  },
};
const AppLayout = ({ children }) => {
  const nodeRef = useRef(null);
  const router = useRouter();
  return (
    <>
      <TransitionGroup style={{ position: 'relative' }}>
        <Transition
          key={router.pathname}
          timeout={{
            enter: TIMEOUT,
            exit: TIMEOUT,
          }}
          nodeRef={nodeRef}
        >
          {(status) => (
            <div
              style={{
                ...getTransitionStyles[status],
              }}
              ref={nodeRef}
            >
              {children}
            </div>
          )}
        </Transition>
      </TransitionGroup>
    </>
  );
};

export default AppLayout;
