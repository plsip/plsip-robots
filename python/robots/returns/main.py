import logging

logging.basicConfig()
logger = logging.getLogger(__name__)
logger.setLevel(logging.INFO)

def handler(event):
    logger.info("Received event: {}".format(event))
    output = {
        'out1': 'val1',
        'out2': 'val2',
    }
    logger.info("Returning response: {}".format(output))
    return output

if __name__ == '__main__':
    handler({'inputParameters': [], 'secretParameters': []})