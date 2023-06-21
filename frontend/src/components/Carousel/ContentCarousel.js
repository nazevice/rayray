import React from 'react';
import Carousel from 'react-bootstrap/Carousel';
import ContentCarouselItem from './ContentCarouselItem';

const ContentCarousel = () => {
    return (
        <Carousel>
           <Carousel.Item>
                <ContentCarouselItem title="test"> 
                ABC
                </ContentCarouselItem>
           </Carousel.Item>
           <Carousel.Item>
           <ContentCarouselItem title="test"> 
                DEF
                </ContentCarouselItem>
           </Carousel.Item>
        </Carousel>
    )
}
export default ContentCarousel;