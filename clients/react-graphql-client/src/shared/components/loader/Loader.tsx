import styles from './Loader.module.scss';
import React from 'react';

export const Loader = () => (
  <div className={styles.Loader + " bg-light"}>
    <div className="spinner-border" role="status">
      <span className="sr-only"/>
    </div>
  </div>
);
