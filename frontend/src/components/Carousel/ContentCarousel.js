import React from 'react';
import Carousel from 'react-bootstrap/Carousel';
import ContentCarouselItem from './ContentCarouselItem';




const ContentCarousel = () => {
    const textStyle = {
        fontFamily: 'Poppins', //Arial, sans-serif
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
        fontSize: '25px'

    };
    return (
        <Carousel>
            <Carousel.Item>
                <ContentCarouselItem>
                    <div style={textStyle}>
                        <div style={{ display: 'flex' }}>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <img src={window.location.origin + '/image/cybersecurity.jpg'} alt="Bild" style={{ width: '200px', height: 'auto' }} />
                            </div>
                            <div style={{ flex: '1', paddingRight: '20px', paddingLeft: '20px', color: 'black', textAlign: 'justify' }}>
                                <h3 style={{ fontWeight: 'bold', fontSize: '40px', marginBottom: '10px' }}>Cybersecurity Konferenz 2023</h3>
                                <p>
                                    Die Cybersecurity-Konferenz findet dieses Jahr in der Winterzeit 2023 statt. Die Konferenz konzentriert sich diesmal auf Themen, die die ganze Welt mit einem einzigen Blick verändern könnten. Wie weit ist KI dieses Jahr entwickelt? Wie können wir sicherstellen, dass wir trotz aller Technologie und Digitalisierung immer noch Menschen bleiben?
                                </p>
                            </div>
                        </div>
                    </div>
                </ContentCarouselItem>
            </Carousel.Item>
            <Carousel.Item>
                <ContentCarouselItem>
                    <div style={textStyle}>
                        <div style={{ display: 'flex' }}>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <img src={window.location.origin + '/image/books.jpg'} alt="Bild" style={{ width: '200px', height: 'auto' }} />
                            </div>
                            <div style={{ flex: '1', paddingRight: '20px', paddingLeft: '20px', color: 'black', textAlign: 'justify' }}>
                                <h3 style={{ fontWeight: 'bold', fontSize: '40px', marginBottom: '10px' }}>Launch des KnowledgeCenters</h3>
                                <p>
                                    Die digitale Bibliothek der Uni Berlin Lebt geht diesen Freitag live. Man hat dann die Möglichkeit, den gesamten Katalog der Werke komplett online durchzuschauen. Die digitale Kollektion kann durch Einloggen auf Ihrem Uni-Konto gelesen und frei heruntergeladen werden. Für die Kollektion, die noch nicht vollständig digitalisiert ist, dürfen Textauszüge angeschaut werden.
                                </p>
                            </div>
                        </div>
                    </div>
                </ContentCarouselItem>
            </Carousel.Item>
            <Carousel.Item>
                <ContentCarouselItem>
                    <div style={textStyle}>
                        <div style={{ display: 'flex' }}>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <img src={window.location.origin + '/image/erdogan.jpg'} alt="Bild" style={{ width: '200px', height: 'auto' }} />
                            </div>
                            <div style={{ flex: '1', paddingRight: '20px', paddingLeft: '20px', color: 'black', textAlign: 'justify' }}>
                                <h3 style={{ fontWeight: 'bold', fontSize: '40px', marginBottom: '10px' }}>Willkommen Professor Yufka!</h3>
                                <p>
                                    Die Uni Berlin Lebt möchte Herrn Prof. Hamudi Abdallah Yufka in unserem tollen Team willkommen heißen. Er bringt sein Fachwissen in Mathematik mit und wir freuen uns, ihn als neues Teammitglied begrüßen zu dürfen. Professor Yufka, wir wünschen Ihnen viel Erfolg und Spaß!
                                </p>
                            </div>
                        </div>
                    </div>
                </ContentCarouselItem>
            </Carousel.Item>
            <Carousel.Item>
                <ContentCarouselItem>
                    <div style={textStyle}>
                        <div style={{ display: 'flex' }}>
                            <div style={{ display: 'flex', alignItems: 'center' }}>
                                <img src={window.location.origin + '/image/party.jpg'} alt="Bild" style={{ width: '200px', height: 'auto' }} />
                            </div>
                            <div style={{ flex: '1', paddingRight: '20px', paddingLeft: '20px', color: 'black', textAlign: 'justify' }}>
                                <h3 style={{ fontWeight: 'bold', fontSize: '40px', marginBottom: '10px' }}>Graduierungsfeier am Kottbusser Tor</h3>
                                <p>
                                    Am Freitag, den 27.10.2023, werden wir voller Stolz unsere Abschlusszeugnisse im Olympia Stadion entgegennehmen, während wir die erfolgreiche Graduierung unserer Klasse GEN-Y feiern. Nach der Zeremonie freuen wir uns auf eine unvergessliche After-Party um 20 Uhr bei McDonald's am Kotti, wo wir gemeinsam lachen, tanzen und unsere Freundschaften stärken können.
                                </p>
                            </div>
                        </div>
                    </div>
                </ContentCarouselItem>
            </Carousel.Item>
        </Carousel>
    )
}
export default ContentCarousel;