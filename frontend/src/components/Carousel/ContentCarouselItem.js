import Carousel from 'react-bootstrap/Carousel';
import { Card, Text, Button } from 'dracula-ui';


const ContentCarouselItem = () => {
    return (
        <Card width="xs" borderWidth="1px" borderColor="gray" borderRadius="4px" p="16px">
            <img src="https://i0.wp.com/post.medicalnewstoday.com/wp-content/uploads/sites/3/2020/03/GettyImages-1092658864_hero-1024x575.jpg" height="200px" />
            <Text fontSize="18px" fontWeight="bold" mt="16px">Class B</Text>
            <Text mt="8px">Lorem ipsum dolor sit amet, consectetur adipiscing elit.</Text>
            <Button mt="16px" variant="primary">Read More</Button>
        </Card>
    )
}
export default ContentCarouselItem;