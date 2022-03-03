import { ImageSize } from './ImageSize';
import { useEffect, useState } from 'react';

interface Props {
  name: string;
  size?: ImageSize;
}

interface Size {
  width: number;
  height: number;
}

const SIZE_MEDIUM: Size = {width: 240, height: 150};
const SIZE_SMALL: Size = {width: 40, height: 40};
const PLACEHOLDER = 'placeholder.png';

export const Image = (props: Props) => {
  const [size, setSize] = useState<Size>(SIZE_MEDIUM);
  const [src, setSrc] = useState<string>();

  useEffect(() => {
    if (props.size === ImageSize.Medium) {
      setSize(SIZE_MEDIUM)
    } else if (props.size === ImageSize.Small) {
      setSize(SIZE_SMALL);
    }
  }, [props.size]);

  useEffect(() => {
    setSrc(`${process.env.REACT_APP_IMAGE_SERVER_URL}/insecure/rs:fit:${size.width}:${size.height}:no:0/plain/s3://catalog-images/${props.name}`);
  }, [size, props.name]);

  const setPlaceholder = () => {
    setSrc(PLACEHOLDER);
  };

  return (
    <img
      src={src}
      alt={props.name}
      style={{
        width: size.width,
        height: size.height
      }}
      onError={setPlaceholder}
    />
  );
};
