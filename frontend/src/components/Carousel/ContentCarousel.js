import React from 'react';
import Carousel from 'react-bootstrap/Carousel';
import ContentCarouselItem from './ContentCarouselItem';

const ContentCarousel = () => {
    return (
        <Carousel>
           <Carousel.Item>
                <ContentCarouselItem />
           </Carousel.Item>
        </Carousel>
    )
}
export default ContentCarousel;